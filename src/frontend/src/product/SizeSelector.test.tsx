import userEvent from "@testing-library/user-event";
import { render } from "../test-utils/test-utils";
import products from "../test-utils/testProducts";
import SizeSelector from "./SizeSelector";

const onSelectedSizeChangeHandler = jest.fn();

describe("<SizeSelector>", () => {
  test("initially renders closed select box", () => {
    const { getByRole, queryAllByRole } = render(
      <SizeSelector
        products={products}
        currentlySelected={1}
        onChangeHandler={onSelectedSizeChangeHandler}
      />
    );

    const listButton = getByRole("button");
    expect(listButton).toBeEnabled();

    const initialListItems = queryAllByRole("option");
    expect(initialListItems.length).toBe(0);
  });

  test("list items appear when button is clicked", async () => {
    const user = userEvent.setup();
    const { getByRole, queryAllByRole, getAllByRole } = render(
      <SizeSelector
        products={products}
        currentlySelected={1}
        onChangeHandler={onSelectedSizeChangeHandler}
      />
    );

    const listButton = getByRole("button");

    const initialListItems = queryAllByRole("option");
    expect(initialListItems.length).toBe(0);

    await user.click(listButton);

    const openListItems = getAllByRole("option");
    expect(openListItems.length).toBe(4);
  });

  test("initial size is already selected", async () => {
    const user = userEvent.setup();
    const { getByRole, getAllByRole } = render(
      <SizeSelector
        products={products}
        currentlySelected={1}
        onChangeHandler={onSelectedSizeChangeHandler}
      />
    );

    const listButton = getByRole("button");
    expect(listButton).toHaveTextContent("M");

    await user.click(listButton);

    const initialListItems = getAllByRole("option");
    expect(initialListItems[0]).toHaveTextContent("M");
  });

  test("different sizes can be selected", async () => {
    const user = userEvent.setup();
    const { getByRole, getAllByRole } = render(
      <SizeSelector
        products={products}
        currentlySelected={0}
        onChangeHandler={onSelectedSizeChangeHandler}
      />
    );

    const listButton = getByRole("button");
    await user.click(listButton);

    let initialListItems = getAllByRole("option");
    await user.click(initialListItems[1]);

    await user.click(listButton);
    initialListItems = getAllByRole("option");

    await user.click(initialListItems[2]);

    expect(onSelectedSizeChangeHandler).toHaveBeenCalledTimes(2);
  });

  test("out of stock items are disabled", async () => {
    const user = userEvent.setup();
    const { getByRole, getAllByRole } = render(
      <SizeSelector
        products={products}
        currentlySelected={1}
        onChangeHandler={onSelectedSizeChangeHandler}
      />
    );

    const listButton = getByRole("button");
    await user.click(listButton);

    const initialListItems = getAllByRole("option");

    expect(initialListItems[3]).toHaveAttribute("aria-disabled", "true");
  });
});
