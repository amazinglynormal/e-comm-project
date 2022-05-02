import { render } from "../test-utils/test-utils";
import ProductsListItem from "./ProductsListItem";
import products from "../test-utils/testProducts";

describe("<ProductListItem>", () => {
  test("renders correct product info", () => {
    const { getByRole, getByText } = render(
      <ProductsListItem product={products[0]} />
    );

    const listItem = getByRole("link");
    expect(listItem).toBeInTheDocument();

    const image = getByRole("img");
    expect(image).toHaveAttribute("src", "test-image-src");
    expect(image).toHaveAttribute("alt", "test image alt");

    const heading = getByRole("heading");
    expect(heading).toHaveTextContent("Test Clothing");

    const priceInfo = getByText("€9.99");
    expect(priceInfo).toBeInTheDocument();
  });

  test("renders link to correct product", () => {
    const { getByRole } = render(<ProductsListItem product={products[1]} />);

    const listItem = getByRole("link");
    expect(listItem).toHaveAttribute("href", "/product/details/2");
  });
});
