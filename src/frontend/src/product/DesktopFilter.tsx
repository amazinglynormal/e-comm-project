import FilterOptions from "../interfaces/filterOptions.interface";

interface Props {
  filters: FilterOptions[];
}

export const DesktopFilter = ({ filters }: Props) => {
  return (
    <div className="hidden lg:block">
      <form className="divide-y divide-gray-200 space-y-10">
        {filters.map((section, sectionIdx) => (
          <div key={section.name} className={sectionIdx === 0 ? "" : "pt-10"}>
            <fieldset>
              <legend className="block text-sm font-medium text-gray-900">
                {section.name}
              </legend>
              <div className="pt-6 space-y-3">
                {section.options.map((option, optionIdx) => (
                  <div key={option.value} className="flex items-center">
                    <input
                      id={`${section.id}-${optionIdx}`}
                      name={`${section.id}[]`}
                      defaultValue={option.value}
                      type="checkbox"
                      className="h-4 w-4 border-gray-300 rounded text-indigo-600 focus:ring-indigo-500"
                    />
                    <label
                      htmlFor={`${section.id}-${optionIdx}`}
                      className="ml-3 text-sm text-gray-600"
                    >
                      {option.label}
                    </label>
                  </div>
                ))}
              </div>
            </fieldset>
          </div>
        ))}
      </form>
    </div>
  );
};
