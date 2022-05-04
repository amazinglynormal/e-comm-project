import { render } from "../test-utils/test-utils";
import RemoveFromCartButton from "./RemoveFromCartButton";
import products from "../test-utils/testProducts";

describe("<RemoveFromCartButton>", () => {
  test("renders correctly", () => {
    const { getByRole } = render(
      <RemoveFromCartButton product={products[0]} />
    );

    const button = getByRole("button");
    expect(button).toHaveTextContent("Remove");
    expect(button).toHaveAttribute("type", "button");
    expect(button).toBeEnabled();
  });
});
