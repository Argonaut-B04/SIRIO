import React from "react";
import Detail from "../../Components/DetailPages/Employee/DetailEmployee";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DetailEmployee extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <Detail />
            </SirioMainLayout>
        );
    }
};