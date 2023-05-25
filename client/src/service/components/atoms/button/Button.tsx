import React from "react";

interface ButtonProps {
    className?: string;
    onClick?: React.MouseEventHandler<HTMLButtonElement>;
    children?: React.ReactNode;
}

const Button = (props: ButtonProps) => {
    const { className, onClick, children } = props;

    return (
        <button className={className} onClick={onClick}>
            {children}
        </button>
    );
};

export default Button;
