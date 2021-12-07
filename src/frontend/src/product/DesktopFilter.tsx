import { ChangeEvent } from "react";
import FilterOptions from "../interfaces/filterOptions.interface";
import FilterForm from "../interfaces/filterForm.interface";
import FilterCheckbox from "./FilterCheckbox";

interface Props {
  filters: FilterOptions[];
  formChangeHandler: (event: ChangeEvent<HTMLInputElement>) => void;
  filterForm: FilterForm;
}

export const DesktopFilter = ({
  filters,
  formChangeHandler,
  filterForm,
}: Props) => {
  return (
    <div className="hidden lg:block">
      <form className="divide-y divide-gray-200 space-y-10">
        {filters.map((category, categoryIdx) => (
          <div key={category.name} className={categoryIdx === 0 ? "" : "pt-10"}>
            <fieldset>
              <legend className="block text-sm font-medium text-gray-900">
                {category.name}
              </legend>
              <div className="pt-6 space-y-3">
                {category.options.map((option) => (
                  <FilterCheckbox
                    key={option.value}
                    category={category}
                    option={option}
                    formChangeHandler={formChangeHandler}
                    filterForm={filterForm}
                  />
                ))}
              </div>
            </fieldset>
          </div>
        ))}
      </form>
    </div>
  );
};
