import userEvent from "@testing-library/user-event";
import FilterForm from "../interfaces/filterForm.interface";
import { render } from "../test-utils/test-utils";
import FilterCheckbox from "./FilterCheckbox";

const filterForm: FilterForm = {
  categories: ["test category", "test category 2"],
  colors: ["black", "white"],
  sizes: ["S", "M"],
};

const category: { id: "categories" | "colors" | "sizes"; name: string } = {
  id: "categories",
  name: "test category",
};

const option = { value: "test value", label: "test label" };

const formChangeHandler = jest.fn();

describe("<FilterCheckbox", () => {
  test("renders correctly", () => {
    const { getByRole, getByLabelText } = render(
      <FilterCheckbox
        category={category}
        option={option}
        formChangeHandler={formChangeHandler}
        filterForm={filterForm}
      />
    );

    const checkbox = getByRole("checkbox");
    expect(checkbox).toBeInTheDocument();
    expect(checkbox).not.toBeChecked();

    const label = getByLabelText("test label");
    expect(label).toBeInTheDocument();
  });

  test("changes checked state when clicked", () => {
    const { getByRole } = render(
      <FilterCheckbox
        category={category}
        option={option}
        formChangeHandler={formChangeHandler}
        filterForm={filterForm}
      />
    );

    const checkbox = getByRole("checkbox");
    expect(checkbox).not.toBeChecked();

    userEvent.click(checkbox);
    expect(checkbox).toBeChecked();

    userEvent.click(checkbox);
    expect(checkbox).not.toBeChecked();
  });
});
