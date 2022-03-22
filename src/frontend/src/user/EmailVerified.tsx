import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { useAppDispatch } from "../hooks/redux-hooks";
import { unwrapResult } from "@reduxjs/toolkit";
import verifyEmail from "../state/async-thunks/verifyEmail";
import Spinner from "../components/Spinner";

interface Params {
  hash: string;
}

const EmailVerified = () => {
  const [verified, setVerified] = useState(false);
  const dispatch = useAppDispatch();
  const { hash } = useParams<Params>();

  useEffect(() => {
    const verify = async () => {
      const result = await dispatch(verifyEmail(hash));
      unwrapResult(result);

      setVerified(true);
    };

    verify();
  });

  return (
    <div>
      {verified ? (
        <div>
          <p>Thank you! Your email has been successfully verified</p>
          <Link to="/login">Go to login --&gt;</Link>
        </div>
      ) : (
        <Spinner />
      )}
    </div>
  );
};

export default EmailVerified;
