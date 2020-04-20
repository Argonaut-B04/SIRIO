import React from "react";
import DetailKantorCabang from "../../Components/DetailPages/KantorCabang/DetailKantorCabang";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class KantorCabang extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <DetailKantorCabang />
            </SirioMainLayout>
        );
    }
};