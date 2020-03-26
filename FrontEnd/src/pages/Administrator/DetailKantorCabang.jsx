import React from "react";
import DetailKantorCabang from "../../Components/DetailPages/KantorCabang/DetailKantorCabang";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DaftarKantorCabang extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <DetailKantorCabang />
            </SirioMainLayout>
        );
    }
};