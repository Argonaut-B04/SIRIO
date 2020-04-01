import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';

export default class DetailBukti extends React.Component {

    data = {
        info: "ini detail bukti",
        kelas: "c"
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
                link="bukti-pelaksanaan"
            />
        );
    }
} 