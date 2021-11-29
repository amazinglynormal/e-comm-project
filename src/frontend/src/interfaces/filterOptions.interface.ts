interface FilterOptions {
  id: "categories" | "colors" | "sizes";
  name: string;
  options: { value: string; label: string }[];
}

export default FilterOptions;
