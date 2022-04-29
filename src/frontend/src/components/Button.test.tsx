import { render } from "../test-utils/test-utils";
import { Button } from "../components/Button";
import { XIcon } from "@heroicons/react/outline";
import userEvent from "@testing-library/user-event";

const clickHandler = jest.fn();

describe("<Button>", () => {
  test("renders correctly", () => {
    const { getByRole } = render(
      <Button
        type="button"
        variant="primary"
        size="medium"
        text="test button"
        onClickHandler={clickHandler}
      />
    );

    const button = getByRole("button");
    expect(button).toBeInTheDocument();
    expect(button).toHaveTextContent("test button");
  });

  test("renders correctly with icon", () => {
    const { getByTestId } = render(
      <Button
        type="button"
        variant="primary"
        size="medium"
        text="test button"
        onClickHandler={clickHandler}
        iconPosition="start"
        icon={<XIcon data-testid="icon" />}
      />
    );

    const buttonIcon = getByTestId("icon");
    expect(buttonIcon).toBeInTheDocument();
  });

  test("triggers clickHandler on click", () => {
    const { getByRole } = render(
      <Button
        type="button"
        variant="primary"
        size="medium"
        text="test button"
        onClickHandler={clickHandler}
      />
    );

    const button = getByRole("button");
    userEvent.click(button);
    userEvent.click(button);

    expect(clickHandler).toBeCalledTimes(2);
  });
});
