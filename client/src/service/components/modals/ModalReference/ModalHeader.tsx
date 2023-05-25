import React from "react";
import Text from "../../atoms/text/Text";
import "./ModalHeader.scss";

interface ModalHeaderProps {
    className?: string;
    textClassName?: string;
    text?: string;
    closer?: () => void;
}

const ModalHeader: React.FC<ModalHeaderProps> = ({
                                                     className = "modal-header",
                                                     textClassName = "modal-header-text",
                                                     text,
                                                 }) => {
    const headerClass = `${className} ${textClassName}`;

    return (
        <div className={headerClass}>
            <Text textClassName={textClassName} text={text}/>
        </div>
    );
};

export default ModalHeader;
