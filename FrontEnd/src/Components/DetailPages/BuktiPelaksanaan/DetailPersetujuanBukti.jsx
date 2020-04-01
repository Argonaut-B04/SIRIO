import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';

export default class DetailPersetujuanBukti extends React.Component {

    data = {
        Keterangan: "Keterangan bukti rekomendasi tess",
        Lampiran: "https://drive.google.com/drive/folders/1SvB_2W4BjD8rxVQR1-dDbA63-4Zx-hoN"
    }

    subButton() {
        return (
            <div>
                <SirioButton purple recommended
                    classes="mx-2"
                    onClick={() => window.location.href = "http://www.google.com"}>
                    Setujui
                </SirioButton>
                <SirioButton purple
                    onClick={() => window.location.href = "/bukti-pelaksanaan/tolak-bukti"}>
                    Tolak
                </SirioButton>
            </div>
        )
    }

    render() {
        return (
            <SirioDetailPage
                title="Bukti Pelaksanaan Rekomendasi"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
                subButton={this.subButton()}
            />
        );
    }
} 