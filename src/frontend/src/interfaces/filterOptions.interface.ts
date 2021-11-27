interface FilterOptions {
  id: "categories" | "colors" | "sizes";
  name: string;
  options: { value: number; label: string }[];
}

export default FilterOptions;
