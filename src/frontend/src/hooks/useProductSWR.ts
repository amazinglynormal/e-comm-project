import axios from "axios";
import useSWR from "swr";

const productsFetcher = async (url: string) => {
  const res = await axios.get(url);
  return res.data;
};

export default function useProductsSWR(categoryId: number, page: number) {
  const { data, error } = useSWR(
    `/api/v1/products?cat=${categoryId}&page=${page}`,
    productsFetcher
  );

  return {
    isError: error,
    isLoading: !error && !data,
    products: data,
  };
}
