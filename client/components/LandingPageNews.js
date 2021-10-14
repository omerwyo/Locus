import { Divider } from "antd";
export default function LandingPageNews(props) {
    return (
        <div className = "mb-5" >
            <div className="flex-row flex">
                <div className="flex-col flex w-1/12" >
                    <span className="opacity-75" color={props.color}>{props.day}</span>
                    <span className="opacity-75" color={props.color}>{props.time}</span>
                </div>
                <div className={`border-l-2 flex-col flex w-full pb-2 pl-1.5 ${props.color === 'white' ? "border-white" : "border-gray-400"}`}>
                    <span className="ml-4 mb-1 font-semibold text-lg" color={props.color}>
                        {props.header}
                    </span>
                    <span className="ml-4" color={props.color}>
                        {props.content}
                    </span>
                </div>
            </div>
        </div>
    );
}
