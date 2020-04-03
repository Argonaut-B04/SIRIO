import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';

export default class DetailBukti extends React.Component {

    data = {
        keterangan: "Keterangan bukti pelaksanaan rekomendasi",
        lampiran: "https://drive.google.com/drive/folders/1SvB_2W4BjD8rxVQR1-dDbA63-4Zx-hoN"
    };

    subButton() {
        return (
            <div>
                <SirioButton purple
                    classes="mx-2 my-2"
                    onClick={() => window.location.href = "/bm/bukti-pelaksanaan/tambah"}
                >
                    Ubah Bukti
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
                subButton={this.subButton()}
            />
        );
    }
} 