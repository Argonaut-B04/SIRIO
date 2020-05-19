import React from "react";
import ProfilKantorCabang from "../../Components/DetailPages/KantorCabang/ProfilKantorCabang.jsx";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class KantorCabang extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <ProfilKantorCabang />
            </SirioMainLayout>
        );
    }
};