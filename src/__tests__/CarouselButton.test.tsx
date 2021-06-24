import { render } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { CarouselButton } from "../components/CarouselButton";

describe("<CarouselButton>", () => {
  test("renders", () => {
    const mockFunc = jest.fn();
    const { getByRole } = render(
      <CarouselButton direction="forward" onClickHandler={mockFunc} />
    );

    expect(getByRole("button")).toBeInTheDocument();
  });

  test("is interactive", () => {
    const mockFunc = jest.fn();
    const { getByRole } = render(
      <CarouselButton direction="forward" onClickHandler={mockFunc} />
    );

    const button = getByRole("button");

    userEvent.click(button);
    expect(mockFunc).toHaveBeenCalledTimes(1);

    userEvent.click(button);
    expect(mockFunc).toHaveBeenCalledTimes(2);
  });
});
