import { render } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { IconButton } from "../components/IconButton";
import { X } from "react-feather";

describe("<IconButton>", () => {
  test("renders correctly", () => {
    const { getByRole } = render(
      <IconButton icon={<X color="black" />} onClickHandler={() => jest.fn()} />
    );

    expect(getByRole("button")).toBeInTheDocument();
  });

  test("is interactive", () => {
    const func = jest.fn();
    const { getByRole } = render(
      <IconButton icon={<X color="black" />} onClickHandler={func} />
    );
    const button = getByRole("button");
    userEvent.click(button);
    expect(func).toHaveBeenCalledTimes(1);
  });
});
