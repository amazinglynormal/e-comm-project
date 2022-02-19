import OrderCostSummary from "./OrderCostSummary";
import { useAppSelector } from "../hooks/redux-hooks";
import { selectOrder } from "../state/orderSlice";
import { TextInput } from "../components/TextInput";
import { ChangeEvent, FormEvent, useState } from "react";
import Address from "../interfaces/Address.interface";
import { useCurrency } from "../state/CurrencyContext";
import axios from "axios";
import { useStripe } from "@stripe/react-stripe-js";

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

interface CreateCheckoutSessionDTO {
  email: string;
  name: string;
  shippingAddress: Address;
  phone: string;
  productIds: number[];
  currency: string;
}

interface CheckoutSession {
  sessionId: string;
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
  const order = useAppSelector(selectOrder);
  const { currency } = useCurrency();
  const stripe = useStripe();

  const [formData, setFormData] = useState<FormData>(initialFormData);

  const onFormChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    setFormData((prevState) => {
      return { ...prevState, [event.target.id]: event.target.value };
    });
  };

  const onSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (!order) return;
    if (!formData.email) return;
    if (
      !formData.line1 ||
      !formData.name ||
      !formData.country ||
      !formData.state
    )
      return;
    if (!formData.phone) return;

    const shippingAddress = {
      line1: formData.line1,
      line2: formData.line2,
      line3: formData.line3,
      city: formData.city,
      province: formData.state,
      country: formData.country,
      zipCode: formData.zipCode,
    };

    const productIds = order.products.map((product) => product.id);

    const createCheckoutSessionDTO: CreateCheckoutSessionDTO = {
      email: formData.email,
      name: formData.name,
      shippingAddress,
      currency: currency.toUpperCase(),
      phone: formData.phone,
      productIds,
    };

    const session = await axios.post<CheckoutSession>(
      "/api/v1/orders/create-checkout-session",
      createCheckoutSessionDTO
    );

    const result = await stripe?.redirectToCheckout({
      sessionId: session.data.sessionId,
    });

    if (result?.error) {
      console.log(result.error.message);
    }
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
                    required
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
                    name="line 2"
                    id="line2"
                    autoComplete="address-line2"
                    value={formData.line2}
                    onChangeHandler={onFormChangeHandler}
                  />
                </div>
                <div className="sm:col-span-2">
                  <TextInput
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
                    required
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
