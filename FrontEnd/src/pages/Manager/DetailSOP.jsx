import React from "react";
import DetailSOP from "../../Components/DetailPages/SOP/DetailSOP";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class SOP extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <DetailSOP />
            </SirioMainLayout>
        );
    }
};