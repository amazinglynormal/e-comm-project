import { render } from "../test-utils/test-utils";
import CartItem from "./CartItem";
import products from "../test-utils/testProducts";

describe("<CartItem>", () => {
  test("renders correctly", () => {
    const { getByRole, getByText } = render(<CartItem product={products[0]} />);

    const image = getByRole("img");
    expect(image).toHaveAttribute("src", "test-image-src");
    expect(image).toHaveAttribute("alt", "test image alt");

    const headerLink = getByRole("link");
    expect(headerLink).toHaveTextContent("Test Clothing");
    expect(headerLink).toHaveAttribute("href", "/product/details/1");

    const colorDesc = getByText("black");
    expect(colorDesc).toBeInTheDocument();

    const sizeDesc = getByText("M");
    expect(sizeDesc).toBeInTheDocument();

    const priceDesc = getByText("€9.99");
    expect(priceDesc).toBeInTheDocument();

    const inStockDesc = getByText("In stock");
    expect(inStockDesc).toBeInTheDocument();
  });
});
