import { ChangeEvent, Dispatch, Fragment, SetStateAction } from "react";
import { Transition, Dialog, Disclosure } from "@headlessui/react";
import { XIcon } from "@heroicons/react/outline";
import { ChevronDownIcon } from "@heroicons/react/solid";

import classNames from "../utils/classNames";
import FilterOptions from "../interfaces/filterOptions.interface";
import FilterForm from "../interfaces/filterForm.interface";
import FilterCheckbox from "./FilterCheckbox";

interface Props {
  mobileFiltersOpen: boolean;
  setMobileFiltersOpen: Dispatch<SetStateAction<boolean>>;
  filters: FilterOptions[];
  formChangeHandler: (event: ChangeEvent<HTMLInputElement>) => void;
  filterForm: FilterForm;
}

export const MobileFilter = ({
  mobileFiltersOpen,
  setMobileFiltersOpen,
  filters,
  formChangeHandler,
  filterForm,
}: Props) => {
  return (
    <Transition.Root show={mobileFiltersOpen} as={Fragment}>
      <Dialog
        as="div"
        className="fixed inset-0 flex z-40 lg:hidden"
        onClose={setMobileFiltersOpen}
      >
        <Transition.Child
          as={Fragment}
          enter="transition-opacity ease-linear duration-300"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="transition-opacity ease-linear duration-300"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <Dialog.Overlay className="fixed inset-0 bg-black bg-opacity-25" />
        </Transition.Child>

        <Transition.Child
          as={Fragment}
          enter="transition ease-in-out duration-300 transform"
          enterFrom="translate-x-full"
          enterTo="translate-x-0"
          leave="transition ease-in-out duration-300 transform"
          leaveFrom="translate-x-0"
          leaveTo="translate-x-full"
        >
          <div className="ml-auto relative max-w-xs w-full h-full bg-white shadow-xl py-4 pb-6 flex flex-col overflow-y-auto">
            <div className="px-4 flex items-center justify-between">
              <h2 className="text-lg font-medium text-gray-900">Filters</h2>
              <button
                type="button"
                className="-mr-2 w-10 h-10 p-2 flex items-center justify-center text-gray-400 hover:text-gray-500"
                onClick={() => setMobileFiltersOpen(false)}
              >
                <span className="sr-only">Close menu</span>
                <XIcon className="h-6 w-6" aria-hidden="true" />
              </button>
            </div>

            {/* Filters */}
            <form className="mt-4">
              {filters.map((category) => (
                <Disclosure
                  as="div"
                  key={category.name}
                  className="border-t border-gray-200 pt-4 pb-4"
                >
                  {({ open }) => (
                    <fieldset>
                      <legend className="w-full px-2">
                        <Disclosure.Button className="w-full p-2 flex items-center justify-between text-gray-400 hover:text-gray-500">
                          <span className="text-sm font-medium text-gray-900">
                            {category.name}
                          </span>
                          <span className="ml-6 h-7 flex items-center">
                            <ChevronDownIcon
                              className={classNames(
                                open ? "-rotate-180" : "rotate-0",
                                "h-5 w-5 transform"
                              )}
                              aria-hidden="true"
                            />
                          </span>
                        </Disclosure.Button>
                      </legend>
                      <Disclosure.Panel className="pt-4 pb-2 px-4">
                        <div className="space-y-6">
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
                      </Disclosure.Panel>
                    </fieldset>
                  )}
                </Disclosure>
              ))}
            </form>
          </div>
        </Transition.Child>
      </Dialog>
    </Transition.Root>
  );
};
