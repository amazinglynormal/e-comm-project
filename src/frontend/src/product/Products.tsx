import { ChangeEvent, useEffect, useState } from "react";
import ProductsList from "./ProductsList";
import { PlusSmIcon } from "@heroicons/react/solid";
import { MobileFilter } from "./MobileFilter";
import { DesktopFilter } from "./DesktopFilter";
import CollectionHeader from "./CollectionHeader";
import useProductsSWR from "../hooks/useProductSWR";
import Shoe from "../interfaces/shoe.interface";
import FilterOptions from "../interfaces/filterOptions.interface";
import FilterForm from "../interfaces/filterForm.interface";
import {
  clothingFilter,
  footwearFilter,
  accessoriesFilter,
} from "../utils/filterOptions";
import { useLocation } from "react-router";
import ListPageSelect from "./ListPageSelect";

const devProducts: Shoe[] = [
  {
    id: 1,
    name: "Earthen Bottle",
    description: "abcd",
    categoryId: 1,
    eur: 48,
    gbp: 48,
    usd: 48,
    imageSrc:
      "https://tailwindui.com/img/ecommerce-images/category-page-04-image-card-01.jpg",
    imageAlt:
      "Tall slender porcelain bottle with natural clay textured body and cork stopper.",
    allSizes: [6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 12, 13],
    availableSizes: [7, 8, 9, 9.5, 11, 12],
    color: "red",
    collection: "trainers",
  },
  {
    id: 2,
    name: "Nomad Tumbler",
    description: "abcd",
    categoryId: 1,
    eur: 35,
    gbp: 35,
    usd: 35,
    imageSrc:
      "https://tailwindui.com/img/ecommerce-images/category-page-04-image-card-02.jpg",
    imageAlt:
      "Olive drab green insulated bottle with flablack screw lid and flat top.",
    allSizes: [6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 12, 13],
    availableSizes: [7, 8, 9, 9.5, 11, 12],
    color: "black",
    collection: "trainers",
  },
  {
    id: 3,
    name: "Focus Paper Refill",
    description: "abcd",
    categoryId: 1,
    eur: 89,
    gbp: 89,
    usd: 89,
    imageSrc:
      "https://tailwindui.com/img/ecommerce-images/category-page-04-image-card-03.jpg",
    imageAlt:
      "Person using a pen to cross a task off a productivity paper card.",
    allSizes: [6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 12, 13],
    availableSizes: [7, 8, 9, 9.5, 11, 12],
    color: "black",
    collection: "trainers",
  },
  {
    id: 4,
    name: "Machined Mechanical Pencil",
    description: "abcd",
    categoryId: 1,
    eur: 35,
    gbp: 35,
    usd: 35,
    imageSrc:
      "https://tailwindui.com/img/ecommerce-images/category-page-04-image-card-04.jpg",
    imageAlt:
      "Hand holding black machined steel mechanical pencil with brass tip and top.",
    allSizes: [6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 12, 13],
    availableSizes: [7, 8, 9, 9.5, 11, 12],
    color: "black",
    collection: "trainers",
  },
  // More products...
];

const defaultFilterForm: FilterForm = {
  categories: [],
  colors: [],
  sizes: [],
};

const Products = () => {
  const location = useLocation();
  const locationArray = location.pathname.split("/");
  const pathname = locationArray[2];
  const categoryChosen = locationArray[3];

  const [page, setPage] = useState(0);

  const [filterOptions, setFilterOptions] =
    useState<FilterOptions[]>(clothingFilter);

  if (categoryChosen) {
    defaultFilterForm.categories = [categoryChosen];
  } else {
    defaultFilterForm.categories = [
      ...filterOptions[0].options.map((option) => option.value),
    ];
  }

  const [filterForm, setFilterForm] = useState<FilterForm>(defaultFilterForm);

  const [mobileFiltersOpen, setMobileFiltersOpen] = useState(false);

  const { data, isLoading, isError } = useProductsSWR(
    filterForm.categories,
    filterForm.colors,
    filterForm.sizes,
    page
  );

  const onPageChange = (newPage: number) => {
    setPage(newPage);
  };

  const onFormChange = (event: ChangeEvent<HTMLInputElement>) => {
    const name = event.target.name;
    const value = event.target.value;

    if (name === "categories") {
      if (filterForm[name].indexOf(value) !== -1) {
        const newCategories = filterForm[name].filter((cat) => cat !== value);
        setFilterForm((prev) => {
          return { ...prev, categories: newCategories };
        });
      } else {
        const newCategories = filterForm[name].slice();
        newCategories.push(value);
        setFilterForm((prev) => {
          return { ...prev, categories: newCategories };
        });
      }
    }

    if (name === "colors") {
      if (filterForm[name].indexOf(value) !== -1) {
        const newColors = filterForm[name].filter((cat) => cat !== value);
        setFilterForm((prev) => {
          return { ...prev, colors: newColors };
        });
      } else {
        const newColors = filterForm[name].slice();
        newColors.push(value);
        setFilterForm((prev) => {
          return { ...prev, colors: newColors };
        });
      }
    }

    if (name === "sizes") {
      if (filterForm[name].indexOf(value) !== -1) {
        const newSizes = filterForm[name].filter((cat) => cat !== value);
        setFilterForm((prev) => {
          return { ...prev, sizes: newSizes };
        });
      } else {
        const newSizes = filterForm[name].slice();
        newSizes.push(value);
        setFilterForm((prev) => {
          return { ...prev, sizes: newSizes };
        });
      }
    }
  };

  useEffect(() => {
    switch (pathname) {
      case "footwear":
        setFilterOptions(footwearFilter);
        break;
      case "accessories":
        setFilterOptions(accessoriesFilter);
        break;
      default:
        setFilterOptions(clothingFilter);
        break;
    }
  }, [pathname]);

  useEffect(() => {
    setFilterForm(defaultFilterForm);
  }, [pathname]);

  useEffect(() => {
    setFilterForm(defaultFilterForm);
  }, [categoryChosen]);

  return (
    <div className="bg-white">
      <div>
        <MobileFilter
          mobileFiltersOpen={mobileFiltersOpen}
          setMobileFiltersOpen={setMobileFiltersOpen}
          filters={filterOptions}
          formChangeHandler={onFormChange}
          filterForm={filterForm}
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

              <DesktopFilter
                filters={filterOptions}
                formChangeHandler={onFormChange}
                filterForm={filterForm}
              />
            </aside>

            {/* Product grid */}
            <div className="lg:mt-0 lg:col-span-2 xl:col-span-3">
              {isLoading ? (
                <div className="text-9xl text-red-900">IS LOADING</div>
              ) : (
                <ProductsList
                  products={isError ? devProducts : data.products}
                />
              )}
              <ListPageSelect
                currentPage={page}
                totalPages={data.totalPages}
                pageChangeHandler={onPageChange}
              />
            </div>
          </div>
        </main>
      </div>
    </div>
  );
};

export default Products;
