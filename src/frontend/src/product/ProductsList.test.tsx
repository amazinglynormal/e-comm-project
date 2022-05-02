import { render } from "../test-utils/test-utils";
import products from "../test-utils/testProducts";
import ProductsList from "./ProductsList";

describe("<ProductsList>", () => {
  test("renders list of products links", () => {
    const { getAllByRole } = render(<ProductsList products={products} />);

    const productList = getAllByRole("link");

    expect(productList[0]).toHaveAttribute("href", "/product/details/1");
    expect(productList[1]).toHaveAttribute("href", "/product/details/2");
    expect(productList[2]).toHaveAttribute("href", "/product/details/3");
    expect(productList[3]).toHaveAttribute("href", "/product/details/4");
  });
});
