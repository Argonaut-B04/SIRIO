import React from "react";
import TabelReminder from "../../Components/Tables/Reminder/TabelReminder";
import SirioMainLayout from "../../Layout/SirioMainLayout";

/**
 * Controller yang menampilkan halaman daftar rekomendasi
 */
export default class Reminder extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TabelReminder />
            </SirioMainLayout>
        );
    }
};