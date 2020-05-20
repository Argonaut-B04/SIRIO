import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelHasilPemeriksaan.module.css';
import SirioTable from '../SirioTable';
import HasilPemeriksaanService from '../../../Services/HasilPemeriksaanService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import SirioMessageButton from "../../Button/ActionButton/SirioMessageButton";

class TabelHasilPemeriksaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            openNotification: true
        };

        this.renderRows = this.renderRows.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    async renderRows() {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");
        const response = await HasilPemeriksaanService.getHasilPemeriksaanByLoggedInUser();

        this.props.changeLoadingBody("Menampilkan data");
        this.setState({
            rowList: response.data.result
        }, this.props.contentFinishLoading())
    }

    endNotification() {
        this.setState({
            openNotification: false
        })
    }

    statusFormatter(cell) {
        switch (cell) {
            case "Draft":
                return (
                    <span style={{ color: '#7F3F98' }}>{cell}</span>
                );
            case "Menunggu Persetujuan":
                return (
                    <span style={{ color: '#8E8E8E' }}>{cell}</span>
                );
            case "Ditolak":
                return (
                    <span style={{ color: '#FF0000' }}>{cell}</span>
                );
            case "Menunggu Pelaksanaan":
                return (
                    <span style={{ color: '#5DBCD2' }}>{cell}</span>
                );
            default:
                return (cell);
        }
    }

    getButtonsFirst(cell, row) {
        const siapDijalankan = row.siapDijalankan;
        const status = row.namaStatus;
        const jalankan = (siapDijalankan === true && status === "Menunggu Pelaksanaan");

        var tombolJalankan =
            <SirioButton
                purple
                disabled
            >
                Jalankan
            </SirioButton>;

        if (jalankan) {
            tombolJalankan =
                <SirioButton
                    purple
                    recommended
                    onClick={(event) => HasilPemeriksaanService.jalankan(row.id).then(() => window.location.reload())}
                >
                    Jalankan
                </SirioButton>
        }

        return (tombolJalankan)
    }

    getButtonsSecond(cell, row) {
        return (
            <NavLink to={{
                pathname: "/hasil-pemeriksaan/detail",
                state: {
                    id: row.id,
                }
            }}>
                <SirioButton
                    purple
                >
                    Detail Hasil
                </SirioButton>
            </NavLink>
        )
    }

    columns = [
        {
            dataField: 'tugasPemeriksaan.namaKantorCabang',
            text: 'HASIL PEMERIKSAAN',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "25%", textAlign: 'left' };
            }
        }, {
            dataField: 'tugasPemeriksaan.id',
            text: 'ID TUGAS',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "15%", textAlign: 'left' };
            }
        }, {
            dataField: 'namaStatus',
            text: 'STATUS',
            sort: true,
            classes: classes.rowItem,
            formatter: this.statusFormatter,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "20%", textAlign: 'left' };
            }
        }, {
            dataField: 'noData 1',
            text: '',
            headerClasses: classes.colheader,
            classes: classes.rowItem,
            formatter: this.getButtonsFirst,
            style: () => {
                return { textAlign: 'center' }
            }
        }, {
            dataField: 'noData 2',
            text: '',
            headerClasses: classes.colheader,
            classes: classes.rowItem,
            formatter: this.getButtonsSecond,
            style: () => {
                return { textAlign: 'center' }
            }
        }];

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    render() {
        return (
            <>
                <SirioTable
                    title="Daftar Hasil Pemeriksaan"
                    data={this.state.rowList}
                    id='id'
                    columnsDefinition={this.columns}
                    includeSearchBar
                />
                {this.props.location.state && this.props.location.state.addSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Hasil Pemeriksaan berhasil Disimpan"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.editSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Hasil Pemeriksaan berhasil Diubah"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.deleteSuccess && this.state.openNotification &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Hasil Pemeriksaan berhasil Dihapus"
                        customConfirmText="Tutup"
                        onClick={this.endNotification}
                    />
                }
                {this.props.location.state && this.props.location.state.setujuSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Hasil Pemeriksaan berhasil Disejutui"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.tolakSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Hasil Pemeriksaan berhasil Ditolak"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
            </>
        );
    }
}

export default withRouter(TabelHasilPemeriksaan);