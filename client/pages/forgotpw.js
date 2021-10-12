import Image from "next/image";
import Navbar from "../components/Navbar";
import { useState, useEffect } from "react";
import { GoogleLogin } from "react-google-login";

import Cookies from "js-cookie";
import FacebookLogin from "react-facebook-login";

export default function Login() {
    return (
        <div className="h-screen w-screen flex-col flex items-center justify-center">
            <div
                style={{
                    backgroundImage: "url(/signup.jpeg)",
                    backgroundPosition: "center",
                    backgroundSize: "cover",
                    backgroundRepeat: "no-repeat",
                    width: "100vw",
                    height: "100vh",
                    opacity: 0.2,
                    position: "absolute",
                    zIndex: -1,
                }}
            ></div>
            <div className="absolute top-0 left-2">
                <Image src="/logo.png" height={100} width={150} />
            </div>
            <div className="flex-col flex border p-5 bg-white rounded-xl shadow-xl">
                <span style={{ fontSize: 35 }} className="mb-1 font-bold">
                    Forgot Password?
                </span>
                <span className="mb-4" style={{ fontSize: 14 }}>
                    Reset password in two quick steps
                </span>
                <input
                    placeholder="Username/Email Address"
                    className="rounded border mb-6 h-14 px-3 w-96 rounded"
                    style={{ fontSize: 13 }}
                />
                
            

                <div
                    className="w-full items-center flex flex-col justify-center h-14 rounded-full"
                    style={{ backgroundColor: "#3295BE", color: "white" }}
                >
                    <span style={{ fontSize: 20 }}>Reset Password</span>
                </div>

                <span className = "self-center mt-6" style={{ fontSize: 17 }}>
                    Back
                </span>
            </div>
            <span className="mt-5">
                New to Locus? <a style={{ color: "#32BEA6" }}>Join Now</a>
            </span>
        </div>
    );
}