import { useState } from "react";
import ProductsList from "./ProductsList";
import { PlusSmIcon } from "@heroicons/react/solid";
import { MobileFilter } from "./MobileFilter";
import { DesktopFilter } from "./DesktopFilter";
// import Shoe from "../interfaces/shoe.interface";
import CollectionHeader from "./CollectionHeader";
import useProductsSWR from "../hooks/useProductSWR";

// const products: Shoe[] = [
//   {
//     id: 1,
//     name: "Earthen Bottle",
//     description: "abcd",
//     categoryId: 1,
//     EUR: 48,
//     GBP: 48,
//     USD: 48,
//     imageSrc:
//       "https://tailwindui.com/img/ecommerce-images/category-page-04-image-card-01.jpg",
//     imageAlt:
//       "Tall slender porcelain bottle with natural clay textured body and cork stopper.",
//     sizeUK: 9,
//     sizeUS: 9,
//     sizeEUR: 9,
//     color: "red",
//     collection: "trainers",
//   },
//   {
//     id: 2,
//     name: "Nomad Tumbler",
//     description: "abcd",
//     categoryId: 1,
//     EUR: 35,
//     GBP: 35,
//     USD: 35,
//     imageSrc:
//       "https://tailwindui.com/img/ecommerce-images/category-page-04-image-card-02.jpg",
//     imageAlt:
//       "Olive drab green insulated bottle with flablack screw lid and flat top.",
//     sizeUK: 9,
//     sizeUS: 9,
//     sizeEUR: 9,
//     color: "black",
//     collection: "trainers",
//   },
//   {
//     id: 3,
//     name: "Focus Paper Refill",
//     description: "abcd",
//     categoryId: 1,
//     EUR: 89,
//     GBP: 89,
//     USD: 89,
//     imageSrc:
//       "https://tailwindui.com/img/ecommerce-images/category-page-04-image-card-03.jpg",
//     imageAlt:
//       "Person using a pen to cross a task off a productivity paper card.",
//     sizeUK: 9,
//     sizeUS: 9,
//     sizeEUR: 9,
//     color: "black",
//     collection: "trainers",
//   },
//   {
//     id: 4,
//     name: "Machined Mechanical Pencil",
//     description: "abcd",
//     categoryId: 1,
//     EUR: 35,
//     GBP: 35,
//     USD: 35,
//     imageSrc:
//       "https://tailwindui.com/img/ecommerce-images/category-page-04-image-card-04.jpg",
//     imageAlt:
//       "Hand holding black machined steel mechanical pencil with brass tip and top.",
//     sizeUK: 9,
//     sizeUS: 9,
//     sizeEUR: 9,
//     color: "black",
//     collection: "trainers",
//   },
//   // More products...
// ];

const filters = [
  {
    id: "color",
    name: "Color",
    options: [
      { value: "white", label: "White" },
      { value: "beige", label: "Beige" },
      { value: "blue", label: "Blue" },
      { value: "brown", label: "Brown" },
      { value: "green", label: "Green" },
      { value: "purple", label: "Purple" },
    ],
  },
  {
    id: "category",
    name: "Category",
    options: [
      { value: "new-arrivals", label: "All New Arrivals" },
      { value: "tees", label: "Tees" },
      { value: "crewnecks", label: "Crewnecks" },
      { value: "sweatshirts", label: "Sweatshirts" },
      { value: "pants-shorts", label: "Pants & Shorts" },
    ],
  },
  {
    id: "sizes",
    name: "Sizes",
    options: [
      { value: "xs", label: "XS" },
      { value: "s", label: "S" },
      { value: "m", label: "M" },
      { value: "l", label: "L" },
      { value: "xl", label: "XL" },
      { value: "2xl", label: "2XL" },
    ],
  },
];

const Products = () => {
  const [mobileFiltersOpen, setMobileFiltersOpen] = useState(false);

  const { products } = useProductsSWR(4, 1);

  return (
    <div className="bg-white">
      <div>
        <MobileFilter
          mobileFiltersOpen={mobileFiltersOpen}
          setMobileFiltersOpen={setMobileFiltersOpen}
          filters={filters}
        />
        <main className="max-w-2xl mx-auto py-16 px-4 sm:py-24 sm:px-6 lg:max-w-7xl lg:px-8">
          <CollectionHeader
            collectionName={"Men's Trainers"}
            collectionDescription={"Men's Trainers description will go here"}
          />

          <div className="pt-12 lg:grid lg:grid-cols-3 lg:gap-x-8 xl:grid-cols-4">
            <aside>
              <h2 className="sr-only">Filters</h2>

              <button
                type="button"
                className="inline-flex items-center lg:hidden"
                onClick={() => setMobileFiltersOpen(true)}
              >
                <span className="text-sm font-medium text-gray-700">
                  Filters
                </span>
                <PlusSmIcon
                  className="flex-shrink-0 ml-1 h-5 w-5 text-gray-400"
                  aria-hidden="true"
                />
              </button>

              <DesktopFilter filters={filters} />
            </aside>

            {/* Product grid */}
            <div className="lg:mt-0 lg:col-span-2 xl:col-span-3">
              <ProductsList products={products} />
            </div>
          </div>
        </main>
      </div>
    </div>
  );
};

export default Products;
