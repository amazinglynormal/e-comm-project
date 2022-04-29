import { render } from "../test-utils/test-utils";
import CollectionHeader from "./CollectionHeader";

describe("<CollectionHeader>", () => {
  test("renders correctly with given props", () => {
    const { getByRole, getByText } = render(
      <CollectionHeader
        collectionName="Test collection"
        collectionDescription="test description"
      />
    );

    const header = getByRole("heading");
    expect(header).toHaveTextContent("Test collection");

    const description = getByText("test description");
    expect(description).toBeInTheDocument();
  });
});
