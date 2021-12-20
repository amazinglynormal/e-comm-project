import axios, { AxiosError } from "axios";
import useSWR from "swr";
import Product from "../types/Product.type";

const fetcher = async (url: string) => {
  const res = await axios.get(url);
  return res.data;
};

const useFetchAltSizes = (productId: string) => {
  const { data, error } = useSWR(
    `api/v1/product/${productId}/altSizes`,
    fetcher
  );

  return {
    isError: error as AxiosError,
    isLoading: !error && !data,
    products: data as Product[],
  };
};

export default function useProductDetailsSWR(productId: string) {
  const { data, error } = useSWR(`/api/v1/products/${productId}`, fetcher);

  const { products, isError, isLoading } = useFetchAltSizes(productId);

  return {
    isError: isError || (error as AxiosError),
    isLoading: (!error && !data) || isLoading,
    data: data as Product,
    products,
  };
}
