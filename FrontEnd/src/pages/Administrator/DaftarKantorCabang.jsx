import React from "react";
import TabelKantorCabang from "../../Components/Tables/KantorCabang/TabelKantorCabang";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DaftarKantorCabang extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TabelKantorCabang/>
            </SirioMainLayout>
        );
    }
};