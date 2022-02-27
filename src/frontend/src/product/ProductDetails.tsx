import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useProductDetailsSWR from "../hooks/useProductDetailsSWR";
import AddToCartButton from "../order/AddToCartButton";
import { useCurrency } from "../state/CurrencyContext";
import SizeSelector from "./SizeSelector";

const currencySymbol: { [index: string]: string } = {
  eur: "€",
  gbp: "£",
  usd: "$",
};

interface IdRouteParam {
  id: string;
}

export default function ProductDetails() {
  const { currency } = useCurrency();

  const { id } = useParams<IdRouteParam>();

  const [selectedSize, setSelectedSize] = useState(Number(id));

  const { data, isLoading, isError, products } = useProductDetailsSWR(id);

  let product = data;

  if (isError) return <div>{isError.message}</div>;

  if (isLoading) return <div>LOADING</div>;

  const onSelectedSizeChange = (id: number) => {
    setSelectedSize(id);
  };

  useEffect(() => {
    const selectedProduct = products.find((p) => p.id === selectedSize);

    if (!selectedProduct) return;

    product = selectedProduct;
  }, [selectedSize]);

  return (
    <div className="bg-white">
      <div className="max-w-2xl mx-auto py-16 px-4 sm:py-24 sm:px-6 lg:max-w-7xl lg:px-8">
        <div className="lg:grid lg:grid-cols-2 lg:gap-x-8 lg:items-start">
          <img
            src={data.imageSrc}
            alt={data.imageAlt}
            className="w-full h-full object-center object-cover sm:rounded-lg"
          />

          {/* Product info */}
          <div className="mt-10 px-4 sm:px-0 sm:mt-16 lg:mt-0">
            <h1 className="text-3xl font-extrabold tracking-tight text-gray-900">
              {data.name}
            </h1>

            <div className="mt-3">
              <h2 className="sr-only">Product information</h2>
              <p className="text-3xl text-gray-900">{`${currencySymbol[currency]}${data[currency]}`}</p>
            </div>

            <div className="mt-6">
              <h3 className="sr-only">Description</h3>

              <div
                className="text-base text-gray-700 space-y-6"
                dangerouslySetInnerHTML={{ __html: data.description }}
              />
            </div>

            <form className="mt-6">
              <div>
                <SizeSelector
                  products={products}
                  currentlySelected={selectedSize}
                  onChangeHandler={onSelectedSizeChange}
                />
              </div>

              <div className="mt-10">
                <AddToCartButton productId={selectedSize} product={product} />
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
