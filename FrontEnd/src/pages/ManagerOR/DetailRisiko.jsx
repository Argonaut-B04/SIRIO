import React from "react";
import DetailRisiko from "../../Components/DetailPages/RegistrasiRisiko/DetailRisiko";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class RegistrasiRisiko extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <DetailRisiko />
            </SirioMainLayout>
        );
    }
};