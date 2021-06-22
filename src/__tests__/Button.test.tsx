import { render } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { Button } from "../components/Button";

describe("<Button>", () => {
  test("renders correctly", () => {
    const mockFunc = jest.fn();
    const { getByRole } = render(
      <Button type="button" text="test button" onClickHandler={mockFunc} />
    );

    const button = getByRole("button");

    expect(button).toBeInTheDocument();
    expect(button).toHaveTextContent("test button");
  });

  test("is interactive", () => {
    const mockFunc = jest.fn();
    const { getByRole } = render(
      <Button type="button" text="test button" onClickHandler={mockFunc} />
    );

    const button = getByRole("button");

    userEvent.click(button);
    expect(mockFunc).toHaveBeenCalledTimes(1);

    userEvent.click(button);
    expect(mockFunc).toHaveBeenCalledTimes(2);
  });
});
