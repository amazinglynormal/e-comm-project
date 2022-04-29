import { render } from "../test-utils/test-utils";
import { CartLink } from "./CartLink";

describe("<CartLink>", () => {
  test("renders correctly", () => {
    const { getByRole } = render(<CartLink />);

    const cartLink = getByRole("link");
    expect(cartLink).toBeInTheDocument();
    expect(cartLink).toHaveTextContent("0 items in cart, view bag");
  });

  test("links to correct cart", () => {});
});
