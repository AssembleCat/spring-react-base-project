import React from "react";
import ReactModal from "react-modal";

import ModalHeader from "./ModalHeader";

import "./ModalReference.scss";

type ModalReferenceProps = {
    className?: string;
    modalBodyClassName?: string;
    modalHeaderClassName?: string;
    headerText?: string;
    children?: React.ReactNode;
    isOpen: boolean;
    closer?: () => void;
};

const ModalReference: React.FC<ModalReferenceProps> = ({
                                                           className,
                                                           modalBodyClassName,
                                                           modalHeaderClassName,
                                                           headerText,
                                                           children,
                                                           isOpen,
                                                           closer,
                                                       }) => {
    const modalClassName = className ? `${className}-modal` : "modal";
    const bodyClassName = modalBodyClassName || `${modalClassName}-body`;

    return (
        <ReactModal
            className={modalClassName}
            isOpen={isOpen}
            style={{ overlay: { height: "100vh" } }}
        >
            <ModalHeader className={modalHeaderClassName} text={headerText} />
            <div className={bodyClassName}>{children}</div>
        </ReactModal>
    );
};

export default ModalReference;
