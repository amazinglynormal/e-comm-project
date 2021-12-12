import axios from "axios";
import useSWR from "swr";
import Product from "../types/Product.type";
import qs from "qs";

interface ReturnData {
  products: Product[];
  totalProducts: number;
  totalPages: number;
}

const productsFetcher = async (url: string) => {
  const res = await axios.get(url);
  return res.data;
};

export default function useProductsSWR(
  categories: string[],
  colors: string[],
  sizes: string[],
  page: number
) {
  categories.sort();
  colors.sort();
  sizes.sort();

  const queryString = qs.stringify(
    { categories, colors, sizes },
    { arrayFormat: "comma" }
  );

  const { data, error } = useSWR(
    `/api/v1/products?${queryString}&page=${page}`,
    productsFetcher
  );

  return {
    isError: error,
    isLoading: !error && !data,
    data: data as ReturnData,
  };
}
