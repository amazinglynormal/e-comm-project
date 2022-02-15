import { useHistory } from "react-router";
import OrderCostSummary from "./OrderCostSummary";
import { useAppSelector } from "../hooks/redux-hooks";
import { selectOrder } from "../state/orderSlice";
import { TextInput } from "../components/TextInput";
import { ChangeEvent, useState } from "react";

interface FormData {
  email: string;
  name: string;
  line1: string;
  line2: string;
  line3: string;
  city: string;
  state: string;
  country: string;
  zipCode: string;
  phone: string;
}

const initialFormData = {
  email: "",
  name: "",
  line1: "",
  line2: "",
  line3: "",
  city: "",
  state: "",
  country: "",
  zipCode: "",
  phone: "",
};

const Checkout = () => {
  const history = useHistory();
  const order = useAppSelector(selectOrder);

  const [formData, setFormData] = useState(initialFormData);

  const onFormChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    setFormData((prevState) => {
      return { ...prevState, [event.target.id]: event.target.value };
    });
  };

  const onSubmit = () => {
    history.push("/ordersummary");
  };

  return (
    <div className="bg-gray-50">
      <div className="max-w-2xl mx-auto pt-16 pb-24 px-4 sm:px-6 lg:max-w-7xl lg:px-8">
        <h2 className="sr-only">Checkout</h2>

        <form
          onSubmit={onSubmit}
          className="lg:grid lg:grid-cols-2 lg:gap-x-12 xl:gap-x-16"
        >
          <div>
            <div>
              <h2 className="text-lg font-medium text-gray-900">
                Contact information
              </h2>

              <div className="mt-4">
                <div className="mt-1">
                  <TextInput
                    type="email"
                    id="email"
                    name="Email"
                    autoComplete="email"
                    value={formData.email}
                    onChangeHandler={onFormChangeHandler}
                  />
                </div>
              </div>
            </div>

            <div className="mt-10 border-t border-gray-200 pt-10">
              <h2 className="text-lg font-medium text-gray-900">
                Shipping information
              </h2>

              <div className="mt-4 grid grid-cols-1 gap-y-6 sm:grid-cols-2 sm:gap-x-4">
                <div className="sm:col-span-2">
                  <TextInput
                    required
                    name="name"
                    id="name"
                    autoComplete="name"
                    value={formData.name}
                    onChangeHandler={onFormChangeHandler}
                  />
                </div>
                <div className="sm:col-span-2">
                  <TextInput
                    required
                    name="line 1"
                    id="line1"
                    autoComplete="address-line1"
                    value={formData.line1}
                    onChangeHandler={onFormChangeHandler}
                  />
                </div>
                <div className="sm:col-span-2">
                  <TextInput
                    required
                    name="line 2"
                    id="line2"
                    autoComplete="address-line2"
                    value={formData.line2}
                    onChangeHandler={onFormChangeHandler}
                  />
                </div>
                <div className="sm:col-span-2">
                  <TextInput
                    required
                    name="line 3"
                    id="line3"
                    autoComplete="address-line3"
                    value={formData.line3}
                    onChangeHandler={onFormChangeHandler}
                  />
                </div>

                <div className="sm:col-span-2">
                  <TextInput
                    required
                    name="city"
                    id="city"
                    autoComplete="address-level2"
                    value={formData.city}
                    onChangeHandler={onFormChangeHandler}
                  />
                </div>

                <div className="sm:col-span-2">
                  <TextInput
                    required
                    name="Province/ State"
                    id="state"
                    autoComplete="address-level1"
                    value={formData.state}
                    onChangeHandler={onFormChangeHandler}
                  />
                </div>

                <div className="sm:col-span-2">
                  <TextInput
                    required
                    name="country"
                    id="country"
                    autoComplete="country-name"
                    value={formData.country}
                    onChangeHandler={onFormChangeHandler}
                  />
                </div>

                <div className="sm:col-span-2">
                  <TextInput
                    required
                    name="zip code"
                    id="zipCode"
                    autoComplete="postal-code"
                    value={formData.zipCode}
                    onChangeHandler={onFormChangeHandler}
                  />
                </div>

                <div className="sm:col-span-2">
                  <TextInput
                    type="text"
                    name="phone"
                    id="phone"
                    autoComplete="tel"
                    value={formData.phone}
                    onChangeHandler={onFormChangeHandler}
                  />
                </div>
              </div>
            </div>
          </div>

          <div>
            <OrderCostSummary
              orderProducts={order?.products || []}
              buttonText="Confirm order"
            />
          </div>
        </form>
      </div>
    </div>
  );
};

export default Checkout;
