interface FilterOptions {
  id: string;
  name: string;
  options: { value: string | number; label: string }[];
}

export default FilterOptions;
