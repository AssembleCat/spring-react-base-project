import React from "react";
import noImage from "../../../../asset/images/noImage.jpg"

interface ImageProps {
    image?: string;
    alt?: string;
    height?: number | string;
    width?: number | string;
    className?: string;
    clickEvent?: React.MouseEventHandler<HTMLImageElement>;
    isFullScreen?: boolean;
    hide?: boolean;
    legacyMode?: boolean;
}

const Image: React.FC<ImageProps> = ({
                                         image ,
                                         alt = noImage,
                                         height,
                                         width,
                                         className,
                                         clickEvent,
                                         isFullScreen,
                                         hide,
                                     }) => {
    let containerClassName = className;
    if (className) {
        containerClassName = `${className}-container`;
    }

    let containerStyle: React.CSSProperties | undefined = undefined;
    if (isFullScreen) {
        // 전체 화면 모드인 경우, 컨테이너의 스타일을 전체 화면 크기로 설정
        containerStyle = {
            height: "100vh",
            width: "100vw",
        };
    }

    let imageStyle: React.CSSProperties | undefined = undefined;
    if (height || width) {
        // height 또는 width 값이 존재하는 경우, 이미지의 스타일을 해당 값으로 설정
        imageStyle = {
            height: height || "auto",
            width: width || "auto",
        };
    }

    if (hide) {
        // hide가 true인 경우, 이미지를 숨김
        return null;
    }

    return (
        <div className={containerClassName} style={containerStyle}>
            <img
                src={image}
                className={className}
                alt={alt}
                onClick={clickEvent}
                style={imageStyle}
            />
        </div>
    );
};

export default Image;
