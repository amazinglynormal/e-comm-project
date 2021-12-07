import FilterOptions from "../interfaces/filterOptions.interface";

const colorOptions: FilterOptions = {
  id: "colors",
  name: "Color",
  options: [
    { value: "BLACK", label: "Black" },
    { value: "BLUE", label: "Blue" },
    { value: "BROWN", label: "Brown" },
    { value: "GREEN", label: "Green" },
    { value: "GREY", label: "Grey" },
    { value: "MULTI", label: "Multi" },
    { value: "NAVY", label: "Navy" },
    { value: "ORANGE", label: "Orange" },
    { value: "PINK", label: "Pink" },
    { value: "PURPLE", label: "Purple" },
    { value: "RED", label: "Red" },
    { value: "TAN", label: "Tan" },
    { value: "WHITE", label: "White" },
    { value: "YELLOW", label: "Yellow" },
  ],
};

const clothingSizes: FilterOptions = {
  id: "sizes",
  name: "Sizes",
  options: [
    { value: "XS", label: "XS" },
    { value: "S", label: "S" },
    { value: "M", label: "M" },
    { value: "L", label: "L" },
    { value: "XL", label: "XL" },
    { value: "2XL", label: "2XL" },
  ],
};

const footwearSizes: FilterOptions = {
  id: "sizes",
  name: "Sizes",
  options: [
    { value: "6", label: "6" },
    { value: "6.5", label: "6.5" },
    { value: "7", label: "7" },
    { value: "7.5", label: "7.5" },
    { value: "8", label: "8" },
    { value: "8.5", label: "8.5" },
    { value: "9", label: "9" },
    { value: "9.5", label: "9.5" },
    { value: "10", label: "10" },
    { value: "10.5", label: "10.5" },
    { value: "11", label: "11" },
    { value: "12", label: "12" },
    { value: "13", label: "13" },
  ],
};

const clothingCategories: FilterOptions = {
  id: "categories",
  name: "Category",
  options: [
    { value: "tshirts", label: "T-shirts" },
    { value: "jeans", label: "Jeans" },
    { value: "shirts", label: "Shirts" },
    { value: "hoodies", label: "Hoodies" },
    { value: "joggers", label: "Joggers" },
    { value: "jackets", label: "Jackets" },
  ],
};

const footwearCategories: FilterOptions = {
  id: "categories",
  name: "Category",
  options: [
    { value: "boots", label: "Boots" },
    { value: "shoes", label: "Shoes" },
    { value: "trainers", label: "Trainers" },
    { value: "sandals/flipflops", label: "Sandals/ Flip-flops" },
    { value: "slippers", label: "Slippers" },
  ],
};

const accessoriesCategories: FilterOptions = {
  id: "categories",
  name: "Category",
  options: [
    { value: "belts", label: "Belts" },
    { value: "sunglasses", label: "Sunglasses" },
    { value: "ties", label: "Ties" },
    { value: "wallets", label: "Wallets" },
  ],
};

export const clothingFilter = [clothingCategories, colorOptions, clothingSizes];
export const footwearFilter = [footwearCategories, colorOptions, footwearSizes];
export const accessoriesFilter = [
  accessoriesCategories,
  colorOptions,
  clothingSizes,
];
