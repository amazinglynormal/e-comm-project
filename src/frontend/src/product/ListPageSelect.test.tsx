import userEvent from "@testing-library/user-event";
import { render } from "../test-utils";
import ListPageSelect from "./ListPageSelect";

const pageChangeHandler = jest.fn();

describe("<ListPageSelect>", () => {
  test("renders correctly when on first page", () => {
    const { getAllByRole } = render(
      <ListPageSelect
        currentPage={0}
        totalPages={10}
        pageChangeHandler={pageChangeHandler}
      />
    );

    const buttons = getAllByRole("button");

    expect(buttons[0]).toBeDisabled();
    expect(buttons[1]).toHaveTextContent("1");
    expect(buttons[2]).toHaveTextContent("2");
    expect(buttons[3]).toHaveTextContent("Next");
  });

  test("renders correctly when not on first page", () => {
    const { getAllByRole } = render(
      <ListPageSelect
        currentPage={4}
        totalPages={10}
        pageChangeHandler={pageChangeHandler}
      />
    );

    const buttons = getAllByRole("button");

    expect(buttons[0]).toHaveTextContent("Previous");
    expect(buttons[1]).toHaveTextContent("4");
    expect(buttons[2]).toHaveTextContent("5");
    expect(buttons[2]).toBeDisabled();
    expect(buttons[3]).toHaveTextContent("6");
    expect(buttons[4]).toHaveTextContent("Next");
  });

  test("pageChangeHandler fires on button click", () => {
    const { getAllByRole } = render(
      <ListPageSelect
        currentPage={4}
        totalPages={10}
        pageChangeHandler={pageChangeHandler}
      />
    );

    const buttons = getAllByRole("button");

    userEvent.click(buttons[0]);
    userEvent.click(buttons[1]);
    userEvent.click(buttons[2]); //buttons[2] should be disabled
    userEvent.click(buttons[3]);
    userEvent.click(buttons[4]);

    expect(pageChangeHandler).toHaveBeenCalledTimes(4);
  });
});
