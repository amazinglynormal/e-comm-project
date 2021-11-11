interface Filter {
  id: string;
  name: string;
  options: { value: string; label: string }[];
}

export default Filter;
