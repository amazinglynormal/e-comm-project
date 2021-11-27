import FilterOptions from "../interfaces/filterOptions.interface";

const colorOptions: FilterOptions = {
  id: "colors",
  name: "Color",
  options: [
    { value: 1, label: "Black" },
    { value: 2, label: "Blue" },
    { value: 3, label: "Brown" },
    { value: 4, label: "Green" },
    { value: 5, label: "Grey" },
    { value: 6, label: "Multi" },
    { value: 7, label: "Navy" },
    { value: 8, label: "Ornage" },
    { value: 9, label: "Pink" },
    { value: 10, label: "Purple" },
    { value: 11, label: "Red" },
    { value: 12, label: "Tan" },
    { value: 13, label: "White" },
    { value: 14, label: "Yellow" },
  ],
};

const clothingSizes: FilterOptions = {
  id: "sizes",
  name: "Sizes",
  options: [
    { value: 1, label: "XS" },
    { value: 2, label: "S" },
    { value: 3, label: "M" },
    { value: 4, label: "L" },
    { value: 5, label: "XL" },
    { value: 6, label: "2XL" },
  ],
};

const footwearSizes: FilterOptions = {
  id: "sizes",
  name: "Sizes",
  options: [
    { value: 6, label: "6" },
    { value: 6.5, label: "6.5" },
    { value: 7, label: "7" },
    { value: 7.5, label: "7.5" },
    { value: 8, label: "8" },
    { value: 8.5, label: "8.5" },
    { value: 9, label: "9" },
    { value: 9.5, label: "9.5" },
    { value: 10, label: "10" },
    { value: 10.5, label: "10.5" },
    { value: 11, label: "11" },
    { value: 12, label: "12" },
    { value: 13, label: "13" },
  ],
};

const clothingCategories: FilterOptions = {
  id: "categories",
  name: "Category",
  options: [
    { value: 1, label: "T-shirts" },
    { value: 2, label: "Jeans" },
    { value: 3, label: "Shirts" },
    { value: 4, label: "Hoodies" },
    { value: 5, label: "Joggers" },
    { value: 6, label: "Jackets" },
  ],
};

const footwearCategories: FilterOptions = {
  id: "categories",
  name: "Category",
  options: [
    { value: 7, label: "Boots" },
    { value: 8, label: "Shoes" },
    { value: 9, label: "Trainers" },
    { value: 10, label: "Sandals/ Flip-flops" },
    { value: 11, label: "Slippers" },
  ],
};

const accessoriesCategories: FilterOptions = {
  id: "categories",
  name: "Category",
  options: [
    { value: 12, label: "Belts" },
    { value: 13, label: "Sunglasses" },
    { value: 14, label: "Ties" },
    { value: 15, label: "Wallets" },
  ],
};

export const clothingFilter = [clothingCategories, colorOptions, clothingSizes];
export const footwearFilter = [footwearCategories, colorOptions, footwearSizes];
export const accessoriesFilter = [
  accessoriesCategories,
  colorOptions,
  clothingSizes,
];
