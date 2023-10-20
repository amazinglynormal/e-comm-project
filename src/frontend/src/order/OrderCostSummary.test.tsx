import { render } from "../test-utils/test-utils";
import OrderCostSummary from "./OrderCostSummary";

const onClickHandler = jest.fn();

describe("<OrderCostSummary>", () => {
  test("renders correctly", () => {
    const { getAllByRole, getByRole } = render(
      <OrderCostSummary
        buttonText="Test button"
        onClickHandler={onClickHandler}
      />
    );

    const heading = getByRole("heading");
    expect(heading).toHaveTextContent("Order summary");

    const link = getByRole("link");
    expect(link).toHaveTextContent(
      "Learn more about how shipping is calculated"
    );
    expect(link).toHaveAttribute("href", "/bluff");

    const terms = getAllByRole("term");
    expect(terms.length).toBe(3);
    expect(terms[0]).toHaveTextContent("Subtotal");
    expect(terms[1]).toHaveTextContent("Shipping estimate");
    expect(terms[2]).toHaveTextContent("Order total");

    // const descriptions = getAllByRole("definition");
    // expect(descriptions.length).toBe(3);
    // expect(descriptions[0]).toHaveTextContent("€45.96");
    // expect(descriptions[1]).toHaveTextContent("€0.00");
    // expect(descriptions[2]).toHaveTextContent("€45.96");

    const button = getByRole("button");
    expect(button).toHaveTextContent("Test button");
    expect(button).toHaveAttribute("type", "button");
    expect(button).toBeEnabled();
  });
});
