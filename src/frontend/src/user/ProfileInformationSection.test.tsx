import { render } from "../test-utils/test-utils";
import ProfileInformationItem from "./ProfileInformationItem";
import ProfileInformationSection from "./ProfileInformationSection";

describe("<ProfileInformationSection>", () => {
  test("renders correctly", () => {
    const { getByRole, getByText } = render(
      <ProfileInformationSection
        heading="Test Heading"
        description="Test description"
      >
        <ProfileInformationItem id="Username" info="Test Username" />
      </ProfileInformationSection>
    );

    const heading = getByRole("heading");
    expect(heading).toHaveTextContent("Test Heading");

    const description = getByText("Test description");
    expect(description).toBeInTheDocument();

    const descTerm = getByRole("term");
    expect(descTerm).toHaveTextContent("Username");

    const descDetails = getByRole("definition");
    expect(descDetails).toHaveTextContent("Test Username");
  });
});
