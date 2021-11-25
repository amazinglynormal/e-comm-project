const colorOptions = {
  id: "color",
  name: "Color",
  options: [
    { value: "black", label: "Black" },
    { value: "blue", label: "Blue" },
    { value: "brown", label: "Brown" },
    { value: "green", label: "Green" },
    { value: "grey", label: "Grey" },
    { value: "multi", label: "Multi" },
    { value: "navy", label: "Navy" },
    { value: "orange", label: "Ornage" },
    { value: "pink", label: "Pink" },
    { value: "purple", label: "Purple" },
    { value: "red", label: "Red" },
    { value: "tan", label: "Tan" },
    { value: "white", label: "White" },
    { value: "yellow", label: "Yellow" },
  ],
};

const clothingSizes = {
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
};

const footwearSizes = {
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

const clothingCategories = {
  id: "category",
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

const footwearCategories = {
  id: "category",
  name: "Category",
  options: [
    { value: "boots", label: "Boots" },
    { value: "shoes", label: "Shoes" },
    { value: "trainers", label: "Trainers" },
    { value: "sandals/flipflops", label: "Sandals/ Flip-flops" },
    { value: "slippers", label: "Slippers" },
  ],
};

const accessoriesCategories = {
  id: "category",
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
