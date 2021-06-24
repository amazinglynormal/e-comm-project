import { render } from "@testing-library/react";

import { CarouselImageContainer } from "../components/CarouselImageContainer";
import pic1 from "../assets/pic1.jpg";

describe("<CarouselImageContainer>", () => {
  test("renders", () => {
    const { getByRole } = render(
      <CarouselImageContainer picture={pic1} altText="T-shirt on a hanger" />
    );

    expect(getByRole("img")).toBeInTheDocument();
  });
});
