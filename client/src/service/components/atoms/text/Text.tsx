import React from "react";

interface TextProps {
    containerClassName?: string;
    textClassName?: string;
    textSize?: number;
    text?: string | number;
    clickEvent?: React.MouseEventHandler;
}

const Text: React.FC<TextProps> = ({
                                       containerClassName,
                                       textClassName,
                                       textSize,
                                       text,
                                       clickEvent,
                                   }) => {
    const defaultText = (text !== null && text !== undefined) ? String(text) : "";
    const splitText = defaultText.split("\n");

    return (
        <div className={containerClassName}>
            {splitText.map((text, index) => (
                <p
                    key={index}
                    className={textClassName}
                    onClick={clickEvent}
                    style={textSize ? {fontSize: textSize} : undefined}
                >
                    {text}
                </p>
            ))}
        </div>
    );
};

export default React.memo(Text);
