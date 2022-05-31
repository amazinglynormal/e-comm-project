import { render, within } from "../test-utils/test-utils";
import OrderHistoryListItem from "./OrderHistoryListItem";
import testOrder from "../test-utils/testOrder";
import Currency from "../enums/Currency.enum";

describe("<OrderHistoryListItem>", () => {
  test("displays header info correctly", () => {
    const { getByRole, getAllByRole } = render(
      <OrderHistoryListItem order={testOrder} currency={Currency.EUR} />
    );

    const srHeader = getByRole("heading");
    expect(srHeader).toHaveTextContent("2022-05-31");

    const terms = getAllByRole("term");
    expect(terms[0]).toHaveTextContent("Date placed");
    expect(terms[1]).toHaveTextContent("Order number");
    expect(terms[2]).toHaveTextContent("Total amount");

    const definitions = getAllByRole("definition");
    expect(definitions[0]).toHaveTextContent("2022-05-31");
    expect(definitions[1]).toHaveTextContent("1");
    expect(definitions[2]).toHaveTextContent("55.95");
  });

  test("displays product info correctly", () => {
    const { getByRole, getAllByRole } = render(
      <OrderHistoryListItem order={testOrder} currency={Currency.EUR} />
    );

    // thead & tbody
    const rowgroups = getAllByRole("rowgroup");
    expect(rowgroups.length).toBe(2);

    // column headers
    const columnHeaders = getAllByRole("columnheader");
    expect(columnHeaders.length).toBe(4);
    expect(columnHeaders[0]).toHaveTextContent("Product");
    expect(columnHeaders[1]).toHaveTextContent("Price");
    expect(columnHeaders[2]).toHaveTextContent("Status");
    expect(columnHeaders[3]).toHaveTextContent("Info");

    // first product row
    const firstProductRow = getAllByRole("row")[1];
    const productImage = within(firstProductRow).getByRole("img");
    expect(productImage).toHaveAttribute("src", "test-image-src");
    expect(productImage).toHaveAttribute("alt", "test image alt");

    const cells = within(firstProductRow).getAllByRole("cell");
    expect(cells.length).toBe(4);
    expect(within(cells[0]).getByText("Test Clothing")).toBeInTheDocument();
    expect(within(cells[0]).getByText("€9.99")).toBeInTheDocument();

    expect(cells[1]).toHaveTextContent("€9.99");
    expect(cells[2]).toHaveTextContent("DELIVERED");

    expect(within(cells[3]).getByRole("link")).toHaveAttribute(
      "href",
      "/product/details/1"
    );
  });
});
