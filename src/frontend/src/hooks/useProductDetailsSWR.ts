import axios from "axios";
import useSWR from "swr";
import Product from "../types/Product.type";

const fetcher = async (url: string) => {
  const res = await axios.get(url);
  return res.data;
};

export default function useProductDetailsSWR(productId: string) {
  const { data, error } = useSWR(`/api/v1/products/${productId}`, fetcher);

  return {
    isError: error,
    isLoading: !error && !data,
    data: data as Product,
  };
}
