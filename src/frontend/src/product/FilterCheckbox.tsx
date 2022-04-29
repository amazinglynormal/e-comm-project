import { ChangeEvent, useState } from "react";
import FilterForm from "../interfaces/filterForm.interface";

interface Props {
  category: { id: "categories" | "colors" | "sizes"; name: string };
  option: { value: string; label: string };
  formChangeHandler: (event: ChangeEvent<HTMLInputElement>) => void;
  filterForm: FilterForm;
}

const FilterCheckbox = ({
  category,
  option,
  formChangeHandler,
  filterForm,
}: Props) => {
  const [checked, setChecked] = useState(
    filterForm[category.id].includes(option.value)
  );

  const onChange = (event: ChangeEvent<HTMLInputElement>) => {
    setChecked((prev) => !prev);
    formChangeHandler(event);
  };

  return (
    <div className="flex items-center">
      <input
        id={`${category.id}-${option.value}`}
        name={`${category.id}`}
        value={option.value}
        type="checkbox"
        className="h-4 w-4 border-gray-300 rounded text-indigo-600 focus:ring-indigo-500"
        onChange={onChange}
        checked={checked}
      />
      <label
        htmlFor={`${category.id}-${option.value}`}
        className="ml-3 text-sm text-gray-600"
      >
        {option.label}
      </label>
    </div>
  );
};

export default FilterCheckbox;
